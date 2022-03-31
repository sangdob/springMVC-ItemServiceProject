package hello.springmvcitemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class ItemRepositoryTest {

	ItemRepository itemRepository = new ItemRepository();

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
		itemRepository.clearStore();
	}

	@Test
	void save() {
		//Given
		Item item = new Item("itemA", 1000, 10);

		//When
		Item savedItem = itemRepository.save(item);

		//Then
		Item findItem = itemRepository.findById(item.getId());
		assertThat(findItem).isEqualTo(savedItem);
	}

	@Test
	void findById() {


	}

	@Test
	void findAll() {
		Item item1 = new Item("item1", 10000, 10);
		Item item2 = new Item("item2", 20000, 20);

		itemRepository.save(item1);
		itemRepository.save(item2);

		List<Item> items = itemRepository.findAll();

		assertThat(items.size()).isEqualTo(2);
		assertThat(items).contains(item1, item2);
	}

	@Test
	void updateItem() {
		Item item = new Item("updateItem", 15000, 20);

		Item saveItem = itemRepository.save(item);
		Long itemId = saveItem.getId();

		Item updateItem = new Item("beforeUpdateItem", 1000, 20);
		itemRepository.update(saveItem.getId(), updateItem);

		Item findItem = itemRepository.findById(itemId);

		assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
		assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());
		assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
	}
}