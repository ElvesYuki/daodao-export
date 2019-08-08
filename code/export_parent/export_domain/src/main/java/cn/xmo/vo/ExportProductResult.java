package cn.xmo.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name="products")
public class ExportProductResult implements Serializable {

	/**
	 * 商品id
	 */
	private String exportProductId;
	/**
	 *税
	 */
	private Double tax;
	
	public String getExportProductId() {
		return exportProductId;
	}
	public void setExportProductId(String exportProductId) {
		this.exportProductId = exportProductId;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ExportProductResult)) return false;
		ExportProductResult that = (ExportProductResult) o;
		return Objects.equals(getExportProductId(), that.getExportProductId()) &&
				Objects.equals(getTax(), that.getTax());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getExportProductId(), getTax());
	}
}
