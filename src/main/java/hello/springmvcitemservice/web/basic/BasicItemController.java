package hello.springmvcitemservice.web.basic;

import hello.springmvcitemservice.domain.item.Item;
import hello.springmvcitemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor        //생성자 제거
public class BasicItemController {

	private final ItemRepository itemRepository;

	//테스트용 초기화값
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("item1", 10000, 10));
		itemRepository.save(new Item("item2", 15000, 30));
		itemRepository.save(new Item("item3", 11000, 40));
		itemRepository.save(new Item("item4", 12000, 40));
	}
	
	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "basic/items";
	}

	@GetMapping("/{itemId}")
	public String items(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}

	@GetMapping("/add")
	public String addForm() {
		return "basic/addForm";
	}

	//@PostMapping("/add")
	public String saveV1(@RequestParam String itemName,
	                   @RequestParam int price,
	                   @RequestParam Integer quentity,
	                   Model model) {
		Item item = new Item();
		item.setItemName(itemName);
		item.setPrice(price);
		item.setQuantity(quentity);

		itemRepository.save(item);

		model.addAttribute("item", item);
		return "basic/item";
	}

//	@PostMapping("/add")
	public String saveV2(@ModelAttribute("item") Item item) {

		itemRepository.save(item);
//		model.addAttribute("item", item); //자동생성 메서드 삭제 가능
		return "basic/item";
	}
//	@PostMapping("/add")
	public String saveV3(@ModelAttribute Item item) {

//		ModelAttribute의 name을 생략할 경우 첫글자가 소문자로 변경된다. Item --> item
		itemRepository.save(item);
//		model.addAttribute("item", item); //자동생성 메서드 삭제 가능
		return "basic/item";
	}

//	@PostMapping("/add")
	public String saveV4(Item item) {

//		ModelAttribute의 name을 생략할 경우 첫글자가 소문자로 변경된다. Item --> item
//		ModelAttribute도 삭제 가능
		itemRepository.save(item);
//		model.addAttribute("item", item); //자동생성 메서드 삭제 가능
		return "basic/item";
	}

//	@PostMapping("/add")
	public String saveV5(Item item) {

//		ModelAttribute의 name을 생략할 경우 첫글자가 소문자로 변경된다. Item --> item
//		ModelAttribute도 삭제 가능
		itemRepository.save(item);
//		model.addAttribute("item", item); //자동생성 메서드 삭제 가능
		return "redirect://basic/item/" + item.getId();
	}

	@PostMapping("/add")
	public String saveV6(Item item, RedirectAttributes redirectAttributes) {
		Item saveItem = itemRepository.save(item);

		redirectAttributes.addAttribute("itemId", saveItem.getId());
		redirectAttributes.addAttribute("status", true);

		return "redirect:/basic/items/{itemId}";
	}



	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item findItem = itemRepository.findById(itemId);
		model.addAttribute("item", findItem);
		return "basic/editForm";
	}

	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
		itemRepository.update(itemId, item);
		return "redirect:/basic/items/{itemId}";
	}
}
