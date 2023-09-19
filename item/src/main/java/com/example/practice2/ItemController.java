package com.example.practice2;

import com.example.practice2.UploadFile.ImageType;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
	private final FileStore fileStore;
	private final ItemRepository itemRepository;
	private final FileRepository fileRepository;

	@GetMapping("item/add")
	public String itemAddForm() {
		return "addItemForm";
	}

	@PostMapping("item/add")
	public String itemSave(@ModelAttribute ItemForm itemForm) throws IOException {
		log.debug("{}",itemForm.getAttachFile());

		// 디비 저장..
		Item item = new Item(itemForm.getItemName(), itemForm.getContents(), new Date());
		itemRepository.save(item);

		UploadFile attachFile = fileStore.storeFile(itemForm.getAttachFile());
		attachFile.setItemId(item);
		attachFile.setImageType(ImageType.MAIN);
		fileRepository.save(attachFile);

		List<UploadFile> storeImageFiles = fileStore.storeFiles(itemForm.getImageFiles());
		for (UploadFile storeImageFile : storeImageFiles) {
			storeImageFile.setItemId(item);
			attachFile.setImageType(ImageType.SUB);
			fileRepository.save(storeImageFile);
		}

		//log.debug("{} {}",item.getItemName(), item.getContent());
		return "addItemForm";
	}

	@GetMapping("/items")
	public String showItemList() {
		List<Item> itemList = itemRepository.findAll();

		return "showItemList";
	}
}
