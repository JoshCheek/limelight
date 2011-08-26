//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.commands;

import limelight.Context;
import limelight.io.Packer;

import java.util.Map;

public class UnpackCommand extends Command
{
  private static Arguments arguments;
  private Packer packer;

  public static Arguments arguments()
  {
    if(arguments == null)
    {
      arguments = new Arguments();
      arguments.addParameter("production", "Path to production file (.llp)");
      arguments.addValueOption("d", "destination", "dir", "Directory where the package will be unpacked.  Defaults to the current working directory (.).");
    }
    return arguments;
  }

  private Packer getPacker()
  {
    if(packer == null)
      packer = new Packer();
    return packer;
  }

  public void setPacker(Packer packer)
  {
    this.packer = packer;
  }

  @Override
  public void doExecute(Map<String, String> args)
  {
    String llpFile = args.get("production");
    String destination = args.get("destination");
    if(destination != null)
      getPacker().unpack(llpFile, destination);
    else
      getPacker().unpack(llpFile, Context.fs().workingDir());
  }

  @Override
  public String description()
  {
    return "Unpack the contents of a .llp production package.";
  }

  @Override
  public String name()
  {
    return "unpack";
  }

  @Override
  public Arguments getArguments()
  {
    return arguments();
  }
}
