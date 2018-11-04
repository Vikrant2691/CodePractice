package adwords.axis.v201802.reportTesting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Table
{
    private Columns columns;

    private Row row;

    
    public Columns getColumns ()
    {
        return columns;
    }
    
    @XmlElement
    public void setColumns (Columns columns)
    {
        this.columns = columns;
    }

    public Row getRow ()
    {
        return row;
    }

    @XmlElement
    public void setRow (Row row)
    {
        this.row = row;
    }

    @Override
    public String toString()
    {
        return "Table [columns = "+columns+", row = "+row+"]";
    }
}
