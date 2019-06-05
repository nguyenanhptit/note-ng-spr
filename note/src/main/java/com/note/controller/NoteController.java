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

import java.util.Optional;

@Controller
public class NoteController {

    @Autowired
    private NoteServiceImpl noteService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @ModelAttribute("categories")
    public Page<Category> categories(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam("search") Optional<String> search,
                             @RequestParam("cate_id") Optional<Long> cate_id,
                             @PageableDefault(value = 5) Pageable pageable) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("note/list");
        Page<Note> notes;
        if (search.isPresent()) {
            notes = noteService.findByTitleOrContent(search.get(), search.get(), pageable);
        } else {
            if (cate_id.isPresent()) {
                Optional<Category> category = categoryService.findById(cate_id.get());
                notes = noteService.findAllByCategory(category, pageable);
                mv.addObject("cate_id", cate_id.get());
            } else {
                notes = noteService.findAll(pageable);
            }
        }



        mv.addObject("notes", notes);
        return mv;
    }

    @RequestMapping(value = "/create-note", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("note/create");
        mv.addObject("note", new Note());
        return mv;
    }

    @RequestMapping(value = "/save-note", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("note") Note note) {
        ModelAndView mv = new ModelAndView();
        noteService.save(note);
        mv.setViewName("note/create");
        mv.addObject("note", new Note());
        mv.addObject("msg", "create successfully !!!");
        return mv;
    }

    @RequestMapping(value = "/edit-note/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        Optional<Note> note = noteService.findById(id);
        mv.setViewName("note/edit");
        mv.addObject("note", note);
        return mv;

    }

    @RequestMapping(value = "/update-note", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("note") Note note) {
        ModelAndView mv = new ModelAndView();
        noteService.save(note);
        mv.setViewName("note/edit");
        mv.addObject("note", note);
        mv.addObject("msg", "update successfully !!!");
        return mv;
    }


    @RequestMapping(value = "/delete-note/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") Long id, @ModelAttribute("note") Optional<Note> note) {
        ModelAndView mv = new ModelAndView();
        note = noteService.findById(id);
        noteService.delete(note.get().getId());
        mv.setViewName("note/delete");
        mv.addObject("note", note);
        mv.addObject("msg", "delete successfully !!!");
        return new ModelAndView("redirect:/notes");

    }

//    @RequestMapping(value = "/delete-note", method = RequestMethod.POST)
//    public ModelAndView delete(@ModelAttribute("note") Note note) {
//        ModelAndView mv = new ModelAndView();
//        noteService.delete(note.getId());
//        mv.setViewName("note/delete");
//        mv.addObject("msg", "delete successfully !!!");
//        return new ModelAndView("redirect:/notes");
//    }


}
