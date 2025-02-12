package cn.hutool.core.text;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StrJoinerTest {

	@Test
	public void joinIntArrayTest(){
		int[] a = {1,2,3,4,5};
		final StrJoiner append = StrJoiner.of(",").append(a);
		assertEquals("1,2,3,4,5", append.toString());
	}

	@Test
	public void joinEmptyTest(){
		List<String> list = new ArrayList<>();
		final StrJoiner append = StrJoiner.of(",").append(list);
		assertEquals("", append.toString());
	}

	@Test
	public void noJoinTest(){
		final StrJoiner append = StrJoiner.of(",");
		assertEquals("", append.toString());
	}

	@Test
	public void joinMultiArrayTest(){
		final StrJoiner append = StrJoiner.of(",");
		append.append(new Object[]{ListUtil.of("1", "2"),
				CollUtil.newLinkedHashSet("3", "4")
		});
		assertEquals("1,2,3,4", append.toString());
	}

	@Test
	public void joinNullModeTest(){
		StrJoiner append = StrJoiner.of(",")
				.setNullMode(StrJoiner.NullMode.IGNORE)
				.append("1")
				.append((Object)null)
				.append("3");
		assertEquals("1,3", append.toString());

		append = StrJoiner.of(",")
				.setNullMode(StrJoiner.NullMode.TO_EMPTY)
				.append("1")
				.append((Object)null)
				.append("3");
		assertEquals("1,,3", append.toString());

		append = StrJoiner.of(",")
				.setNullMode(StrJoiner.NullMode.NULL_STRING)
				.append("1")
				.append((Object)null)
				.append("3");
		assertEquals("1,null,3", append.toString());
	}

	@Test
	public void joinWrapTest(){
		StrJoiner append = StrJoiner.of(",", "[", "]")
				.append("1")
				.append("2")
				.append("3");
		assertEquals("[1,2,3]", append.toString());

		append = StrJoiner.of(",", "[", "]")
				.setWrapElement(true)
				.append("1")
				.append("2")
				.append("3");
		assertEquals("[1],[2],[3]", append.toString());
	}

	@Test
	public void lengthTest(){
		StrJoiner joiner = StrJoiner.of(",", "[", "]");
		assertEquals(joiner.toString().length(), joiner.length());

		joiner.append("123");
		assertEquals(joiner.toString().length(), joiner.length());
	}

	@Test
	public void mergeTest(){
		StrJoiner joiner1 = StrJoiner.of(",", "[", "]");
		joiner1.append("123");
		StrJoiner joiner2 = StrJoiner.of(",", "[", "]");
		joiner1.append("456");
		joiner1.append("789");

		final StrJoiner merge = joiner1.merge(joiner2);
		assertEquals("[123,456,789]", merge.toString());
	}

	@Test
	public void issue3444Test() {
		final StrJoiner strJoinerEmpty = StrJoiner.of(",");
		assertEquals(0, strJoinerEmpty.length());

		final StrJoiner strJoinerWithContent = StrJoiner.of(",").append("haha");
		assertEquals(4, strJoinerWithContent.length());
	}
}
