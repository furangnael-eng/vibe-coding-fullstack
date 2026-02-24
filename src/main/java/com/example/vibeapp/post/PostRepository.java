package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 게시글 저장 (영속화)
     * JPA의 persist()를 사용하여 새로운 엔티티를 영속성 컨텍스트에 저장합니다.
     */
    public void save(Post post) {
        if (post.getNo() == null) {
            em.persist(post);
        } else {
            em.merge(post);
        }
    }

    /**
     * ID로 게시글 조회
     * em.find()를 사용하여 1차 캐시 또는 DB에서 엔티티를 찾습니다.
     */
    public Post findById(Long no) {
        return em.find(Post.class, no);
    }

    /**
     * 게시글 삭제
     * em.remove()를 사용하여 영속성 컨텍스트 및 DB에서 엔티티를 삭제합니다.
     */
    public void deleteById(Long no) {
        Post post = findById(no);
        if (post != null) {
            em.remove(post);
        }
    }

    /**
     * 전체 게시글 수 조회
     * JPQL을 사용하여 전체 카운트를 쿼리합니다.
     */
    public int countAll() {
        return em.createQuery("SELECT count(p) FROM Post p", Long.class)
                .getSingleResult()
                .intValue();
    }

    /**
     * 조회수 증가
     * 엔티티를 조회한 후 필드 값을 변경하면, 트랜잭션 종료 시 변경 감지(Dirty Checking) 기능에 의해 업데이트됩니다.
     */
    public void incrementViews(Long no) {
        Post post = findById(no);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
    }

    /**
     * 페이징된 게시글 목록 조회
     * JPQL과 setFirstResult, setMaxResults를 사용하여 페이징을 구현합니다.
     */
    public List<Post> findPaged(int offset, int limit) {
        return em.createQuery("SELECT p FROM Post p ORDER BY p.no DESC", Post.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
