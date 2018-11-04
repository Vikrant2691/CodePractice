package adwords.axis.v201802.reportTesting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

public class Daterange
{
	
    private String date;

    public String getDate ()
    {
        return date;
    }

    @XmlAttribute
    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "Daterange [date = "+date+"]";
    }
}