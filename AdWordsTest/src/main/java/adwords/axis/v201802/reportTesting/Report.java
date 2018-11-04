package adwords.axis.v201802.reportTesting;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Report
{
    private Reportname reportname;
    private Daterange daterange;
    private Table table;


	public Table getTable() {
		return table;
	}

	@XmlElement
	public void setTable(Table table) {
		this.table = table;
	}


	public Reportname getReportname() {
		return reportname;
	}


	@XmlElement
	public void setReportname(Reportname reportname) {
		this.reportname = reportname;
	}



	public Daterange getDaterange() {
		return daterange;
	}


	@XmlElement
	public void setDaterange(Daterange daterange) {
		this.daterange = daterange;
	}

	@Override
	public String toString() {
		return "Report [reportname=" + reportname + ", daterange=" + daterange + ", table=" + table + "]";
	}



	

    
}