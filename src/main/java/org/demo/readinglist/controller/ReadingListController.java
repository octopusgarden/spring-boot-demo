package org.demo.readinglist.controller;

import java.util.List;

import org.demo.readinglist.database.Book;
import org.demo.readinglist.database.Reader;
import org.demo.readinglist.database.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/readingList")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReadingListController {
    private final ReadingListRepository readingListRepository;
    private final AmazonProperties amazonProperties;

    @RequestMapping(method = RequestMethod.GET)
    public String readersBooks(final Reader reader, final Model model) {
        final List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
        }
        return "readingList";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addToReadingList(final Reader reader, final Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/";
    }
}

