package com.admeliora.briefbot.comment.tool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentToolProvider {

//    private final CommentRepository commentRepository;

//    @Tool(
//            name = "fetchAllBookComments",
//            description = "Retrieves a complete list of all book-related comments from the system."
//    )
    public List<String> fetchAllCommentsMCP() {
//        List<Comment> comments = commentRepository.findAll();
//        return comments;
        return new ArrayList<String>();
    }
//    @McpTool(name = "add", description = "Add two numbers together")
//    public int add(
//            @McpToolParam(description = "First number", required = true) int a,
//            @McpToolParam(description = "Second number", required = true) int b) {
//        return a + b;
//    }
//    @Tool()
}