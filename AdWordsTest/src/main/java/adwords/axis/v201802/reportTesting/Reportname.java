package adwords.axis.v201802.reportTesting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


public class Reportname{
	

    private String name;

    public String getName ()
    {
        return name;
    }

    @XmlAttribute
    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Reportname [name = "+name+"]";
    }
}