
package orbit;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class StringMessage extends AbstractMessage {
	private String message;
	public StringMessage() {}
	public StringMessage(String msg) { message = msg; }
	public String getMessage() { return message; }
}
