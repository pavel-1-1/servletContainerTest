package org.example.repository;

import org.example.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepository {

    private static List<Post> posts = new ArrayList<>();
    private static long counter = 0;

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(Post post) {
        synchronized (posts) {
            for (Post post1 : posts) {
                if (post1.getId() == post.getId()) {
                    post1.setContent(post.getContent());
                    return Optional.of(post);
                }
            }
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        synchronized (posts) {
            if (post.getId() == 0 && post.getContent() != null) {
                counter++;
                post.setId(counter);
                posts.add(post);
                return posts.get(posts.size() - 1);
            }
        }
        return new Post(0, null);
    }

    public boolean removeById(long id) {
        synchronized (posts) {
            return posts.removeIf(n -> n.getId() == id);
        }
    }
}
