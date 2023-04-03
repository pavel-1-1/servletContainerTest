package org.example.repository;

import org.example.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    private final AtomicLong counter = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(Post post) {
        if (posts.containsKey(post.getId())) {
            posts.put(post.getId(), post);
            return Optional.of(post);
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        if (post.getId() == 0 && post.getContent() != null) {
            counter.getAndIncrement();
            post.setId(counter.get());
            posts.put(counter.get(), post);
            return posts.get(counter.get());
        }
        return new Post(0, null);
    }

    public boolean removeById(long id) {
        if (posts.remove(id) == null) {
            return false;
        }
        return true;
    }
}
