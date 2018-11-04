package adwords.axis.v201802.reportTesting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;


public class Columns
{
	
    private Column[] column;

    public Column[] getColumn ()
    {
        return column;
    }
    
    @XmlElement
    public void setColumn (Column[] column)
    {
        this.column = column;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [column = "+column[0]+"]";
    }
}