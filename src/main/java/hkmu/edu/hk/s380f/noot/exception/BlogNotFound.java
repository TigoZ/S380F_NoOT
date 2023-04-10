package hkmu.edu.hk.s380f.noot.exception;

public class BlogNotFound extends Exception {
    public BlogNotFound(long id) {
        super("Blog " + id + " does not exist.");
    }
}

