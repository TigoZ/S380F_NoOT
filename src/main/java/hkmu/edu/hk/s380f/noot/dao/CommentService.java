package hkmu.edu.hk.s380f.noot.dao;

import hkmu.edu.hk.s380f.noot.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
