package io.sample.bean.model;

import java.io.Serializable;
import java.util.Map;

public class ChargeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long chargeTotalSum;
	private Long nowChargeSum;
	private Map<String, Long> gameDomain;

	public Long getChargeTotalSum() {
		return chargeTotalSum;
	}
	public void setChargeTotalSum(Long chargeTotalSum) {
		this.chargeTotalSum = chargeTotalSum;
	}
	public Long getNowChargeSum() {
		return nowChargeSum;
	}
	public void setNowChargeSum(Long nowChargeSum) {
		this.nowChargeSum = nowChargeSum;
	}
	public Map<String, Long> getGameDomain() {
		return gameDomain;
	}
	public void setGameDomain(Map<String, Long> gameDomain) {
		this.gameDomain = gameDomain;
	}

}
