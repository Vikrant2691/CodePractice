package adwords.axis.v201802.reportTesting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Row")
public class Row
{
	@XmlAttribute
    private String clientName;

	@XmlAttribute
    private String autoTaggingEnabled;

	@XmlAttribute
    private String account;

    @XmlAttribute
    private String timeZone;

    @XmlAttribute
    private String canManageClients;

    @XmlAttribute
    private String customerID;

    @XmlAttribute
    private String currency;

    public String getClientName ()
    {
        return clientName;
    }

    public void setClientName (String clientName)
    {
        this.clientName = clientName;
    }

    public String getAutoTaggingEnabled ()
    {
        return autoTaggingEnabled;
    }

    public void setAutoTaggingEnabled (String autoTaggingEnabled)
    {
        this.autoTaggingEnabled = autoTaggingEnabled;
    }

    public String getAccount ()
    {
        return account;
    }

    public void setAccount (String account)
    {
        this.account = account;
    }

    public String getTimeZone ()
    {
        return timeZone;
    }

    public void setTimeZone (String timeZone)
    {
        this.timeZone = timeZone;
    }

    public String getCanManageClients ()
    {
        return canManageClients;
    }

    public void setCanManageClients (String canManageClients)
    {
        this.canManageClients = canManageClients;
    }

    public String getCustomerID ()
    {
        return customerID;
    }

    public void setCustomerID (String customerID)
    {
        this.customerID = customerID;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [clientName = "+clientName+", autoTaggingEnabled = "+autoTaggingEnabled+", account = "+account+", timeZone = "+timeZone+", canManageClients = "+canManageClients+", customerID = "+customerID+", currency = "+currency+"]";
    }
}