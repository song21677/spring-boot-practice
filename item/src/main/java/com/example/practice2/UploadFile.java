package com.example.practice2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UploadFile {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item itemId;

	// length는??
	@Column(nullable = false)
	private String uploadFileName;

	@Column(nullable = false, unique = true)
	private String storeFileName;

	@Enumerated(EnumType.STRING)
	private ImageType imageType;

	public UploadFile(String uploadFileName, String storeFileName) {
		this.uploadFileName = uploadFileName;
		this.storeFileName = storeFileName;
	}

	public void setItemId(Item itemId) {
		this.itemId = itemId;
	}

	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}

	enum ImageType {
		MAIN, SUB
	}
}