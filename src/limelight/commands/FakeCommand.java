package limelight.commands;

import java.util.Map;

public class FakeCommand extends Command
{
  public Map<String, String> args;
  private static Arguments arguments = new Arguments();

  @Override
  public void doExecute(Map<String, String> args)
  {
    this.args = args;
  }

  @Override
  public String description()
  {
    return "fake description";
  }

  @Override
  public String name()
  {
    return "fake";
  }

  @Override
  public Arguments getArguments()
  {
    return arguments;
  }

  @Override
  public void help()
  {
    say("Fake command help");
  }
}
