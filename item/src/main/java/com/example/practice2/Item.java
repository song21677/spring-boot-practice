package com.example.practice2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(
		name = "ITEM_SEQ_GENERATOR",
		sequenceName = "ITEM_SEQ",
		initialValue = 1, allocationSize = 1)
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
									generator = "ITEM_SEQ_GENERATOR")
	private Long id;

	@Column(nullable = false, length = 50)
	private String itemName;

	@OneToOne(mappedBy = "itemId")
	private UploadFile attachFile;

	@OneToMany(mappedBy = "itemId")
	private List<UploadFile> uploadFile = new ArrayList<>(7);

	String contents;
	@Temporal(TemporalType.DATE)
	private Date timestamp;

	public Item(String itemName, String contents, Date date) {
		this.itemName = itemName;
		this.contents = contents;
		this.timestamp = date;
	}
}