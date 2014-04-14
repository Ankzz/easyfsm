/**
 *                      GNU Public License
 * Copyright (C) 2014 Free Software Foundation, Inc. <http://fsf.org>
 * 
 * This file is part of library EasyFSM.
 * 
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. This library can be redistributed
 * or used in case this license is copied as it is.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Author : Ankit
 * Report bugs to : hiiankit (at) gmail (dot) com
**/
package States;

import Common.CustomXMLReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Implements a State for FSM
 * 
 * @author ANKIT
 */
public class FSMState {
    
    private String  _curState;
    private HashMap _transitionMap; 
    private String  _configFileName;
    private CustomXMLReader _reader;
    
    /**
     * This Constructor allows to create a FSM with the initial state 
     * specified
     * <br/>
     * 
     * @param state Specifies initial state of FSM
     * @param configFileName Specifies the XML Configuration filename
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public FSMState(String state, String configFileName) 
            throws ParserConfigurationException, SAXException, IOException {
        this._curState = state;
        this._configFileName = configFileName;
        this._reader = new CustomXMLReader(
                this.getClass().getClassLoader().getResourceAsStream(
                this._configFileName)
                );
    }

    /**
     * This Constructor allows to create an FSM with initial state set
     * to specified value "state" and sets the transitions as per the 
     * transition map passed onto to it.<br/>
     * 
     * @param state Initial state of the FSM
     * @param map Transition Map of states.
     */
    public FSMState(String state, HashMap map){
        this._curState = state;
        this._transitionMap = map;
    }
    
    /**
     * Method to allow addition of Messages along with their own
     * corresponding Action
     * 
     * @param message
     * @param action
     */
    public void addMessages(String message, Object action) {
        this._transitionMap.put(message, action);
    }
    
    /**
     * Method implicitly generates the Transition Map from
     * the specified XML Configuration file.<br/>
     * 
     */
    public void addMessagesAndActions() {
        File _f = new File( this._configFileName );
        if ( _f.exists()  && !_f.isDirectory() && this._reader != null) {
            this._transitionMap = this._reader.getStateInfo(this._curState);
        }
    }
    
    /**
     *  Method to return the entire Transition Map<br/>
     * @return
     */
    public HashMap getTransitionMap() { return this._transitionMap;}
    
    /**
     * Method to return Current State of the FSM
     * @return
     */
    public String getCurrentState() { return this._curState; }
    
}
