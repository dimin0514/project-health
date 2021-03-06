package com.health.web.brd;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.web.pxy.Trunk;

@RestController
@RequestMapping("/brds")
public class BrdCtl {
	@Autowired BrdMapper brdMapper;
	@Autowired Trunk<Object> trunk;
	
	@PutMapping("/")
	public Map<?,?> writeBrd(@RequestBody Brd param){
		System.out.println("글쓰기 들어옴"+ param.getContent());
		Consumer<Brd> c = t -> brdMapper.insertBrd(t);
		c.accept(param);
		
		Supplier<String> s =()->  brdMapper.countArtseq();
		trunk.put(Arrays.asList("msg","count"),Arrays.asList("SUCCESS",s.get()));
		return trunk.get();
	}
	
	@GetMapping("/list")
	public List<Brd> list(){
		Supplier<List<Brd>> s= ()-> brdMapper.selectAll();
		return s.get(); 
	}
	
	@GetMapping("/read/{seq}")
	public Brd readBrd(@PathVariable String seq) {
		Supplier<Brd> c = ()-> brdMapper.selectBrd(seq);
		return c.get();
	}
	
	@PutMapping("/update/{seq}")
	public Brd updateBrd(@PathVariable String seq, @RequestBody Brd param) {
		System.out.println("수정 들어옴");
		Consumer<Brd> c = t -> brdMapper.updateBrd(param);
		c.accept(param);
		Supplier<Brd> d = ()-> brdMapper.selectBrd(seq);
		return d.get();
		
	}
	
	@DeleteMapping("/{seq}")
	public Map<?,?> deleteBrd(@PathVariable String brdseq,@RequestBody Brd param){
		Consumer<Brd> c = t-> brdMapper.deleteBrd(param);
		c.accept(param);
		trunk.put(Arrays.asList("msg"), Arrays.asList("success"));
		return trunk.get();
	}
}
