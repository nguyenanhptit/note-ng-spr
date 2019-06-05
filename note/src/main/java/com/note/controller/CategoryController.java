package com.note.controller;

import com.note.model.Category;
import com.note.model.Note;
import com.note.service.impl.CategoryServiceImpl;
import com.note.service.impl.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private NoteServiceImpl noteService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @RequestMapping("/view-category/{id}")
    public ModelAndView listCategory(@PathVariable("id")Long id, @PageableDefault(value = 100) Pageable pageable){
        ModelAndView mv = new ModelAndView();
        Optional<Category> category = categoryService.findById(id);

        if (category == null){
            mv.setViewName("error/404");
        }else {
            Page<Note> notes = noteService.findAllByCategory(category,pageable);
            mv.setViewName("category/view");
            mv.addObject("notes",notes );
            mv.addObject("category", category);

        }
        return mv;

    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam("search") Optional<String> search, @PageableDefault(value = 5) Pageable pageable) {
        ModelAndView mv = new ModelAndView();
        Page<Category> categories;
        if (search.isPresent()) {
            categories = categoryService.findByName(search.get(), pageable);
        } else {
            categories = categoryService.findAll(pageable);
        }
        mv.setViewName("category/list");
        mv.addObject("categories", categories);
        return mv;
    }

    @RequestMapping(value = "/create-category", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("category/create");
        mv.addObject("category", new Category());
        return mv;
    }

    @RequestMapping(value = "/save-category", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("category") Category category) {
        ModelAndView mv = new ModelAndView();
        categoryService.save(category);
        mv.setViewName("category/create");
        mv.addObject("category", new Category());
        mv.addObject("msg", "create successfully !!!");
        return mv;
    }

    @RequestMapping(value = "/edit-category/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        Optional<Category> category = categoryService.findById(id);
        mv.setViewName("category/edit");
        mv.addObject("category", category);
        return mv;

    }

    @RequestMapping(value = "/update-category", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("category") Category category) {
        ModelAndView mv = new ModelAndView();
        categoryService.save(category);
        mv.setViewName("category/edit");
        mv.addObject("category", category);
        mv.addObject("msg", "update successfully !!!");
        return mv;
    }


    @RequestMapping(value = "/delete-category/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long id, @ModelAttribute("category") Optional<Category> category, Pageable pageable) {
        ModelAndView mv = new ModelAndView();
        category = categoryService.findById(id);
        Page<Note> notes = noteService.findAllByCategory(category,pageable);
        List<Note> note = notes.getContent();
        if (note.isEmpty()){
            categoryService.delete(category.get().getId());
            mv.addObject("msg", "delete successfully !!!");

        }else {
            mv.addObject("msg","Can't delete cause Category is mapping !!!");
        }
        mv.setViewName("redirect:/categories");
        mv.addObject("category", category);
        return mv;
    }
//
//    @RequestMapping(value = "/delete-category", method = RequestMethod.POST)
//    public ModelAndView delete(@ModelAttribute("category") Category category) {
//        ModelAndView mv = new ModelAndView();
//        categoryService.delete(category.getId());
//        mv.setViewName("category/delete");
//        mv.addObject("msg", "delete successfully !!!");
//        return new ModelAndView("redirect:/categories");
//    }


}
