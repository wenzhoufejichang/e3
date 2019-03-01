package e3_cart_interface;

import java.util.List;
import java.util.Set;

import com.hzm.daomain.TbItem;

public interface SettlementInterface {

	public abstract List<TbItem> buy(Set<String> set);

}
