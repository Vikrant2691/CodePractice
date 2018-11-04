package adwords.axis.v201802.reportTesting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

public class Column
{
	
    private String name;

	
    private String display;

    public String getName ()
    {
        return name;
    }

    @XmlAttribute
    public void setName (String name)
    {
        this.name = name;
    }

    public String getDisplay ()
    {
        return display;
    }

    @XmlAttribute
    public void setDisplay (String display)
    {
        this.display = display;
    }

	@Override
	public String toString() {
		return "Column [name=" + name + ", display=" + display + "]";
	}

    
}