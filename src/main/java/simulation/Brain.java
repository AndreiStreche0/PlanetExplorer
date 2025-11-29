package simulation;

import fileio.CommandInput;
import fileio.SimulationInput;
import planet.Matrix;
import terrabot.TerraBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public class Brain {
    private List<SimulationInput> simulationsParameters;
    private Simulation currentSimulation;
    private int simulationNumber;
    private List<CommandInput> commands;
    private final ObjectMapper objectMapper;
    private final ArrayNode outputNode;

    public Brain(List<SimulationInput> simulationsParameters, List<CommandInput> commands, ObjectMapper objectMapper, ArrayNode outputNode) {
        this.simulationsParameters= simulationsParameters;
        this.currentSimulation = null;
        this.simulationNumber = -1;
        this.commands = commands;
        this.objectMapper = objectMapper;
        this.outputNode = outputNode;
    }

    public void processCommand(CommandInput commandInput) {
        String commandName = commandInput.command;
        int commandTimeStamp = commandInput.timestamp;
        String outputString = "";
        boolean simulationRunning = currentSimulation != null && currentSimulation.isRunning();
        if (!simulationRunning && !commandName.equals("startSimulation")) {
            outputString = "ERROR: Simulation not started. Cannot perform action";
        } else {
            while (simulationRunning && currentSimulation.getTimeStamp() < commandTimeStamp) {
                if (currentSimulation.getTerraBot().isCharging()) {
                    currentSimulation.getTerraBot().charge();
                }
                currentSimulation.evolve();
            }
            if (simulationRunning && currentSimulation.getTimeStamp() >= commandTimeStamp
                    && currentSimulation.getTerraBot().isCharging()) {
                outputString = "ERROR: Robot still charging. Cannot perform action";
                addMessageOutput(outputString, commandTimeStamp, commandName);
                return;
            }
            switch (commandName) {
                case "startSimulation":
                    if (simulationRunning) {
                        outputString = "ERROR: Simulation already started. Cannot perform action";
                    } else {
                        this.simulationNumber++;
                        currentSimulation = new Simulation(simulationsParameters.get(simulationNumber));
                        currentSimulation.start();
                        outputString = "Simulation has started.";
                    }
                    break;
                case "endSimulation":
                    currentSimulation.stop();
                    outputString = "Simulation has ended.";
                    break;
                case "moveRobot":
                    outputString = currentSimulation.moveRobot();
                    break;
                case "rechargeBattery":
                    outputString = currentSimulation.chargeRobot(commandInput.timeToCharge);
                    break;
                case "changeWeatherConditions":
                    outputString = currentSimulation.changeWeather(commandInput, commandInput.type);
                    break;
                case "scanObject":
                    outputString = currentSimulation.scanObject(commandInput.color
                            , commandInput.smell, commandInput.sound);
                    break;
                case "learnFact":
                    outputString = currentSimulation.learnFact(commandInput.components, commandInput.subject);
                    break;
                case "improveEnvironment":
                    outputString = currentSimulation.improveEnvironment(commandInput.improvementType, commandInput.name);
                    break;
                case "getEnergyStatus":
                    outputString = currentSimulation.getEnergyStatus();
                    break;
                case "printEnvConditions":
                    ObjectNode envOutput = currentSimulation.printEnvConditions();
                    addObjectOutput(envOutput, commandTimeStamp, commandName);
                    return;
                case "printMap":
                    addArrayObjectOutput(currentSimulation.printMap(), commandTimeStamp, commandName);
                    return;
                case "printKnowledgeBase":
                    ArrayNode knowledgeOutput = currentSimulation.printKnowledgeBase();
                    addArrayObjectOutput(knowledgeOutput, commandTimeStamp, commandName);
                    return;
                default:
                    outputString = "ERROR: Wrong command";
            }
        }
        addMessageOutput(outputString, commandTimeStamp, commandName);
    }

    public void addArrayObjectOutput(ArrayNode outputArray, int timeStamp, String commandName) {
        ObjectNode outputObject = objectMapper.createObjectNode();
        outputObject.put("command", commandName);
        outputObject.put("output", outputArray);
        outputObject.put("timestamp", timeStamp);
        outputNode.add(outputObject);
    }

    public void addObjectOutput (ObjectNode newNode, int timeStamp, String commandName) {
        ObjectNode outputObject = objectMapper.createObjectNode();
        outputObject.put("command", commandName);
        outputObject.put("output", newNode);
        outputObject.put("timestamp", timeStamp);
        outputNode.add(outputObject);
    }

    public void addMessageOutput (String outputMessage, int timeStamp, String commandName) {
        ObjectNode outputObject = objectMapper.createObjectNode();
        outputObject.put("command", commandName);
        outputObject.put("message", outputMessage);
        outputObject.put("timestamp", timeStamp);
        outputNode.add(outputObject);
    }

    public void parseCommands() {
        for (CommandInput command : commands) {
            processCommand(command);
        }
    }
}
