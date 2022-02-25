package com.deu.football_love.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.deu.football_love.dto.post.UpdatePostRequest;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post extends BaseEntity {

  @Column(name = "post_id")
  @GeneratedValue
  @Id
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_author", referencedColumnName = "member_number")
  private Member author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @Column(name = "post_title")
  private String title;

  @Column(name = "post_content")
  private String content;

  @OneToMany(mappedBy = "post")
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "post")
  private List<PostImage> postImages = new ArrayList<>();

  @OneToMany(mappedBy = "post")
  private List<PostLike> postLikes = new ArrayList<>();

  public Post() {}

  public Post(Member author, TeamBoard board, String title, String content) {
    this.author = author;
    this.board = board;
    this.title = title;
    this.content = content;
  }

  /**
   * 수정해야함
   *
   */
  public void update(UpdatePostRequest request) {
    this.setTitle(request.getTitle());
    this.setContent(request.getContent());
  }

  public void deletePost() {
    author.getPosts().remove(this);
    this.author = null;
    board.getPosts().remove(this);
    this.board = null;
  }

  public PostImage addPostImage(String imageUri) {
    PostImage postImage = new PostImage(imageUri, this);
    this.getPostImages().add(postImage);
    return postImage;
  }
}
