package org.springframework.samples.mvc.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "tbl_news")  
public class News  {
	/** ���� ����MySQL */  
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "id", nullable = false)    
    private Long id;   
    
	@Column(name = "title", nullable = false)    
    private String title;
    
	@Column(name = "content", nullable = false)    
    private String content;
	
//	@ManyToOne(optional = false,fetch = FetchType.LAZY)//�����ڡ�һ����pojo������ֶ���     (fetch = FetchType.LAZY,optional = true,cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
//	@JoinColumn(name = "userId" , referencedColumnName = "id")//���ö�Ӧ���ݱ�����������õ����ݱ������     , referencedColumnName = "id"
//	private User userId;    
	
	@Column(name = "user_ID")
	private Long userId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

//	public User getUserId() {
//		return userId;
//	}
//
//	public void setUserId(User userId) {
//		this.userId = userId;
//	}


	
	
}
