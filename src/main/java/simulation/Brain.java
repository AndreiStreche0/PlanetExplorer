package simulation;

import fileio.CommandInput;
import fileio.SimulationInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

/**
 * handles the logic behind this project
 * coordinates the robot, the simulations and the map
 */
public class Brain {
    private final List<SimulationInput> simulationsParameters;
    private Simulation currentSimulation;
    private int simulationNumber;
    private final List<CommandInput> commands;
    private final ObjectMapper objectMapper;
    private final ArrayNode outputNode;

    public Brain(final List<SimulationInput> simulationsParameters,
                 final List<CommandInput> commands, final ObjectMapper objectMapper,
                 final ArrayNode outputNode) {
        this.simulationsParameters = simulationsParameters;
        this.currentSimulation = null;
        this.simulationNumber = -1;
        this.commands = commands;
        this.objectMapper = objectMapper;
        this.outputNode = outputNode;
    }

    /**
     * handles the command and the output
     * @param commandInput command input
     */
    public void processCommand(final CommandInput commandInput) {
        String commandName = commandInput.getCommand();
        int commandTimeStamp = commandInput.getTimestamp();
        String message;
        boolean simulationRunning = currentSimulation != null && currentSimulation.isRunning();
        if (!simulationRunning && !commandName.equals("startSimulation")) {
            message = "ERROR: Simulation not started. Cannot perform action";
        } else {
            while (simulationRunning && currentSimulation.getTimeStamp() < commandTimeStamp) {
                if (currentSimulation.getTerraBot().isCharging()) {
                    currentSimulation.getTerraBot().charge();
                }
                currentSimulation.evolve();
            }
            if (simulationRunning && currentSimulation.getTimeStamp() >= commandTimeStamp
                    && currentSimulation.getTerraBot().isCharging()) {
                message = "ERROR: Robot still charging. Cannot perform action";
                addMessageOutput(message, commandTimeStamp, commandName);
                return;
            }
            switch (commandName) {
                case "startSimulation":
                    if (simulationRunning) {
                        message = "ERROR: Simulation already started. Cannot perform action";
                    } else {
                        this.simulationNumber++;
                        currentSimulation =
                                new Simulation(simulationsParameters.get(simulationNumber));
                        currentSimulation.start();
                        message = "Simulation has started.";
                    }
                    break;
                case "endSimulation":
                    currentSimulation.stop();
                    message = "Simulation has ended.";
                    break;
                case "moveRobot":
                    message = currentSimulation.moveRobot();
                    break;
                case "rechargeBattery":
                    message = currentSimulation.chargeRobot(commandInput.getTimeToCharge());
                    break;
                case "changeWeatherConditions":
                    message =
                            currentSimulation.changeWeather(commandInput, commandInput.getType());
                    break;
                case "scanObject":
                    message = currentSimulation.scanObject(commandInput.getColor(),
                            commandInput.getSmell(), commandInput.getSound());
                    break;
                case "learnFact":
                    message = currentSimulation.learnFact(commandInput.getComponents(),
                            commandInput.getSubject());
                    break;
                case "improveEnvironment":
                    String type = commandInput.getImprovementType();
                    message = currentSimulation.improveEnvironment(type, commandInput.getName());
                    break;
                case "getEnergyStatus":
                    message = currentSimulation.getEnergyStatus();
                    break;
                case "printEnvConditions":
                    ObjectNode envOutput = currentSimulation.printEnvConditions();
                    addObjectOutput(envOutput, commandTimeStamp, commandName);
                    return;
                case "printMap":
                    addArrayObjectOutput(currentSimulation.printMap(), commandTimeStamp,
                            commandName);
                    return;
                case "printKnowledgeBase":
                    ArrayNode knowledgeOutput = currentSimulation.printKnowledgeBase();
                    addArrayObjectOutput(knowledgeOutput, commandTimeStamp, commandName);
                    return;
                default:
                    message = "ERROR: Wrong command";
            }
        }
        addMessageOutput(message, commandTimeStamp, commandName);
    }

    /**
     * adds an array to the output object
     * @param outputArray array of nodes for output
     * @param timeStamp timeStamp of command
     * @param commandName name of command
     */
    public void addArrayObjectOutput(final ArrayNode outputArray, final int timeStamp,
                                     final String commandName) {
        ObjectNode outputObject = objectMapper.createObjectNode();
        outputObject.put("command", commandName);
        outputObject.put("output", outputArray);
        outputObject.put("timestamp", timeStamp);
        outputNode.add(outputObject);
    }
    /**
     * adds an object to the output object
     * @param newNode node for output
     * @param timeStamp timeStamp of command
     * @param commandName name of command
     */
    public void addObjectOutput(final ObjectNode newNode, final int timeStamp,
                                final String commandName) {
        ObjectNode outputObject = objectMapper.createObjectNode();
        outputObject.put("command", commandName);
        outputObject.put("output", newNode);
        outputObject.put("timestamp", timeStamp);
        outputNode.add(outputObject);
    }
    /**
     * adds a message to the output object
     * @param outputMessage message for output
     * @param timeStamp timeStamp of command
     * @param commandName name of command
     */
    public void addMessageOutput(final String outputMessage, final int timeStamp,
                                 final String commandName) {
        ObjectNode outputObject = objectMapper.createObjectNode();
        outputObject.put("command", commandName);
        outputObject.put("message", outputMessage);
        outputObject.put("timestamp", timeStamp);
        outputNode.add(outputObject);
    }

    /**
     * parses all the commands
     */
    public void parseCommands() {
        for (CommandInput command : commands) {
            processCommand(command);
        }
    }
}
