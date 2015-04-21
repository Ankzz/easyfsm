# EasyFSM - A Java Library to facilitate Finite State Machine (FSM) creation.

## Contents

.toc

## Description

This library facilitates creation of a Finite State Machine (FSM).
Library generates a Finite State Machine (FSM) from a configuration
file specified while invoking the object for the FSM class.

Current library has been built as Java Library Project in Netbeans 7.0.1

## To build

Source code (EasyFSM.zip) can be extracted and built in a Netbeans IDE 
or can even be ported to an Eclipse environment.

## To Use Library

Add EasyFSM.jar to the Java Library folder of your project or Java Installation.
Ensure that the CLASSPATH variable of your build environment has the EasyFSM.jar
in its path.

## Usage

Just a test example for usage (Example1.java):

    /**
     * Example1 Code exemplifies the usage of FSM with a fixed path XML configuration file 
     **/
    import Action.FSMAction;
    import FSM.FSM;
    
    public class Example1 {
        public static void testFSM() {
            try {
                FSM f = new FSM("C://config.xml", new FSMAction() {
                    @Override
                    public boolean action(String curState, String message, String nextState, Object args) {
                        javax.swing.JOptionPane.showMessageDialog(null, curState + ":" + message + " : " + nextState);
                        /*
                         * Here we can implement our login of how we wish to handle
                         * an action
                         */
                        return true;
                    }
                });
                System.out.println(f.getCurrentState());
                f.ProcessFSM("MOVELEFT");
                System.out.println(f.getCurrentState());
                f.ProcessFSM("MOVE");
                System.out.println(f.getCurrentState());
                f.ProcessFSM("MOVERIGHT");
                System.out.println(f.getCurrentState());
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(TestOwnCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(TestOwnCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TestOwnCode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
        public static void main(String[] args) {
            try {
                testFSM();
            } catch (Exception ex) {
                Logger.getLogger(TestOwnCode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 

Another example (Example2.java):

    /**
     * Example2 Code exemplifies the usage of FSM with a XML configuration file specified
     * within the project as resource.
     **/
    import Action.FSMAction;
    import FSM.FSM;
    
    public class Example2 {
    
        private String _configFileName = "resource/config.xml";
    
        public static void testFSM() {
            try {
                FSM f = new FSM(this.getClass().getClassLoader().getResourceAsStream(_configFileName), new FSMAction() {
                    @Override
                    public boolean action(String curState, String message, String nextState, Object args) {
                        javax.swing.JOptionPane.showMessageDialog(null, curState + ":" + message + " : " + nextState);
                        /*
                         * Here we can implement our login of how we wish to handle
                         * an action
                         */
                        return true;
                    }
                });
                System.out.println(f.getCurrentState());
                f.ProcessFSM("MOVELEFT");
                System.out.println(f.getCurrentState());
                f.ProcessFSM("MOVE");
                System.out.println(f.getCurrentState());
                f.ProcessFSM("MOVERIGHT");
                System.out.println(f.getCurrentState());
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(TestOwnCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(TestOwnCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TestOwnCode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
        public static void main(String[] args) {
            try {
                testFSM();
            } catch (Exception ex) {
                Logger.getLogger(TestOwnCode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

XML Configuration file should be of the following format:

    <?xml version="1.0" encoding="UTF-8"?>
    
    <!--
        Document   : config.xml
        Created on : 22 March, 2013, 9:05 AM
        Author     : ANKIT
        Description:
            File specifies states and transition of an FSM.
            This is an example file.
    -->
    
    <FSM>
            <STATE id="START" type="ID">
                    <MESSAGE id="MOVE" action="move" nextState="START" />
                    <MESSAGE id="MOVELEFT" action="moveLeft" nextState="INTERMEDIATE" />
                    <MESSAGE id="MOVERIGHT" action="moveRight" nextState="STOP" />
            </STATE>
            <STATE id="INTERMEDIATE">
                    <MESSAGE id="MOVELEFT" action="moveLeft" nextState="STOP" />
                    <MESSAGE id="MOVERIGHT" action="moveRight" nextState="ANKIT" />
            </STATE>
            <STATE id="STOP" />
            <STATE id="ANKIT" />
    </FSM>

And here is a diagram representing the transitions for this finite state machine:
[diagram]

/---------------------\
| START               |
\---------------------/
  |
  +-----------------+                    /---------------------\
  | MOVE            |------------------->| START               |
  +-----------------+                    \---------------------/
  |                move 
  |
  +-----------------+                    /---------------------\
  | MOVELEFT        |------------------->| INTERMEDIATE        |
  +-----------------+                    \---------------------/
  |                moveLeft 
  |
  +-----------------+                    /---------------------\
  | MOVERIGHT       |------------------->| STOP                |
  +-----------------+                    \---------------------/
                   moveRight 

/---------------------\
| INTERMEDIATE        |
\---------------------/
  |
  +-----------------+                    /---------------------\
  | MOVELEFT        |------------------->| STOP                |
  +-----------------+                    \---------------------/
  |                moveLeft 
  |
  +-----------------+                    /---------------------\
  | MOVERIGHT       |------------------->| ANKIT               |
  +-----------------+                    \---------------------/
                   moveRight

/---------------------\
| STOP                |
\---------------------/

/---------------------\
| ANKIT               |
\---------------------/

[/diagram]
