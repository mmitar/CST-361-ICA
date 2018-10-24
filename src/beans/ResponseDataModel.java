package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author speed
 *
 */
@XmlRootElement(name="Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseDataModel extends ResponseModel {
	
    private Album data;
    
    /**
     * @param status
     * @param message
     */
    public ResponseDataModel() 
    {
        super(0, "");
        this.data = null;
    }

    /**
     * @param status
     * @param message
     * @param data
     */
    public ResponseDataModel(int status, String message, Album data) 
    {
        super(status, message);
        this.data = data;
    }

    /**
     * @return the data
     */
    public Album getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Album data) {
        this.data = data;
    }
    
}