package com.kja.exam.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kja.exam.demo.vo.Article;


@Controller
public class UsrHomeController {
	@RequestMapping("/usr/home/getString")
	@ResponseBody
	public String getString() {
		return "HI";
	}

	@RequestMapping("/usr/home/getInt")
	@ResponseBody
	public int getInt() {
		return 10;
	}

	@RequestMapping("/usr/home/getFloat")
	@ResponseBody
	public float getFloat() {
		return 10.5f;
	}

	@RequestMapping("/usr/home/getDouble")
	@ResponseBody
	public double getDouble() {
		return 10.5;
	}

	@RequestMapping("/usr/home/getBoolean")
	@ResponseBody
	public boolean getBoolean() {
		return true;
	}

	@RequestMapping("/usr/home/getCharacter")
	@ResponseBody
	public char getCharacter() {
		return 'a';
	}

	@RequestMapping("/usr/home/getMap")
	@ResponseBody
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("철수나이", 22);
		map.put("영희나이", 21);

		return map;
	}

	@RequestMapping("/usr/home/getList")
	@ResponseBody
	public List<String> getList() {
		List<String> list = new ArrayList<>();
		list.add("철수");
		list.add("영희");

		return list;
	}

	@RequestMapping("/usr/home/getArticle")
	@ResponseBody
	public Article getArticle() {
		Article artricle = new Article(1, "제목1", "내용1");

		return artricle;
	}
	
	@RequestMapping("/usr/home/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		Article artricle1 = new Article(1, "제목1", "내용1");
		Article artricle2 = new Article(2, "제목2", "내용2");

		List<Article> list = new ArrayList<>();
		list.add(artricle1);
		list.add(artricle2);

		return list;
	}
}
