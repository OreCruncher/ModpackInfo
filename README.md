###Build Status
Master Branch: [![Master Branch](https://travis-ci.org/OreCruncher/ModpackInfo.svg?branch=master)](https://travis-ci.org/OreCruncher/ModpackInfo)

### ModpackInfo

When added to a modpack ModpackInfo will do the following things:

* It will generate a log file to the ./minecraft directory which contains information about each of the loaded mods.  The information is sourced from the mods themselves.  The format of the output file can be configured to text, XML, json, or BBCode.
* If configured to do so a player will be sent modpack information via in-game chat when they log in.  This information would have to be configured by the modpack authors prior to deployment.
* Players will have access to a /modpackinfo command in-game.  This command will retrieve additional information about the modpack that has been deployed.

Further information can be found on the [Wiki](https://github.com/OreCruncher/ModpackInfo/wiki).

###License
The MIT License (MIT)

Copyright (c) 2015 OreCruncher

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
