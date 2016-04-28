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

import Action.FSMAction;
import Common.CustomXMLReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Implements the list of FSM States
 * 
 * <p>
 * This implementation caters to the need of maintaining the state of
 * of a FSM.
 * </p>
 * 
 * @version 1.00
 * @author ANKIT
 */
public class FSMStates implements java.io.Serializable {
    
    private ArrayList _fsmStates;
    private ArrayList _states;
    private FSMState _curState;
    private String _configFileName="config/config.xml";
    
    /**
     * <p>
     * This constructor allows to create a FSM from a Configuration File<br/>
     * This constructor allows a developer to have flexibility of specifying
     * an external XML Configuration file with a definite path.
     * </p>
     * <br/>
     * 
     * @param configFName
     * @param extFile 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public FSMStates(String configFName, boolean extFile) 
            throws ParserConfigurationException, SAXException, IOException {
        
        if(!"".equals(configFName)) this._configFileName = configFName;
        this._fsmStates = new ArrayList<FSMState>();
        CustomXMLReader _r = null;
        if(!extFile) {
            _r = new CustomXMLReader(
                this.getClass().getClassLoader().getResourceAsStream(
                this._configFileName)
                );
        } else {
            _r = new CustomXMLReader( this._configFileName );
        }
        
        _states = _r.getStates();
        
        for (Object _state : _states) {
            HashMap _t = _r.getStateInfo((String)_state);
            this._fsmStates.add(new FSMState ((String) _state, _t));
        }
        
        this._curState = (FSMState) this._fsmStates.get(0);
    }

    /**
     * <p>
     * This constructor allows to create a FSM from a InputStream<br/>
     * This InputStream can be a :
     * <ol>
     *  <li> InputStream of a XML configuration file.
     * </ol>
     * <br/>
     * This constructor allows a developer to have flexibility of specifying
     * InputStream of a resource defined within a project.
     * </p>
     * <br/>
     * 
     * @param configFStream InputStream of a XML Configuration file
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public FSMStates(InputStream configFStream) 
            throws ParserConfigurationException, SAXException, IOException {
        CustomXMLReader _r = new CustomXMLReader(configFStream);
        this._fsmStates = new ArrayList<FSMState>();
        _states = _r.getStates();
        
        for (Object _state : _states) {
            HashMap _t = _r.getStateInfo((String)_state);
            this._fsmStates.add(new FSMState ((String) _state, _t));
        }
        
        this._curState = (FSMState) this._fsmStates.get(0);
    }
    
    /**
     * This constructor uses the default configuration file.
     * <br/>
     * This Constructor shall not be used and still exists to just test
     * or understand the behavior.
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @deprecated 
     */
    @Deprecated
    public FSMStates() 
            throws ParserConfigurationException, SAXException, IOException {
        this("", false);
    }

    /**
     * This method allows to set the current state of the FSM
     * <br/>
     * 
     * @param f
     */
    public void setCurrentState(FSMState f) { this._curState = f; }
    
    /**
     * This method allows to set specific action methods for a specific
     * message/action.<br/> 
     * If specified, this method shall be called when specified message is
     * received in specified list of states.
     * <br/>
     *
     * @param states List of states for which specified action method needs to
     *               be initiated
     * @param message Message/Action which is received 
     * @param act Action method which needs to be initiated when message/action
     *            is received
     */
    public void setAction(ArrayList<String> states, String message, 
            FSMAction act) {
        int count = states.size();
        for (Iterator it = this._fsmStates.iterator(); it.hasNext();) {
            FSMState i = (FSMState) it.next();
            if (states.contains(i.getCurrentState())) {
                i.addMessageAction(message, act);
                count--;
                if (count<=0) break;
            }
        }
    }
    
    /**
     * This method allows to set specific action methods for a specific
     * message/action.<br/> 
     * If specified, this method shall be called when specified message is
     * received in specified state.
     * <br/>
     *
     * @param state State for which this override action method needs to be 
     *              initiated
     * @param message Message/Action which is received 
     * @param act Action method which needs to be initiated when message/action
     *            is received
     */
    public void setAction(String state, String message, 
            FSMAction act) {
        setAction(new ArrayList(Arrays.asList(state)), message, act);
    }
    
    /**
     * This method allows to set specific action methods for a specific
     * message/action.<br/> 
     * If specified, this method shall be called when specified message is
     * received in any state.
     * <br/>
     * 
     * @param message Message/Action which is received 
     * @param act Action method which needs to be initiated when message/action
     *            is received
     */
    public void setAction(String message, FSMAction act) {
        ArrayList states = (ArrayList) getAllStates();
        int count = states.size();
        for (Iterator it = this._fsmStates.iterator(); it.hasNext();) {
            FSMState i = (FSMState) it.next();
            if (states.contains(i)) {
                i.addMessageAction(message, act);
                count--;
                if (count<=0) break;
            }
        }    
    }
    
    /**
     * This method allows to get the current state of the FSM
     * <br/>
     * 
     * @return Returns a FSMState object
     */
    public FSMState getCurrentState() { return this._curState;}
    
    /**
     * This method returns the list a FSM States configured for this FSM.
     * <br/>
     * 
     * @return Returns the list of FSM states
     */
    public List getAllStates() { return this._fsmStates;}
}
