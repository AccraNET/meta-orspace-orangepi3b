# INTRODUCTION
u-boot is a fully featured bootloader.  
It has a rich commandline interface called the HUSH shell


## HUSH Shell

### help
Used to retrieve information on the available commands

### echo
Usefule for printing text. 

### bdinfo
Probes and prints the system information

### 'mw' / 'md' (Memory access commands)
Useful for reading and writing memory and peripheral registers.  
mw => memory write.  
md => memory display.  

`mw [address] [data]`  
`md [address] [size]`  

On the rk3566, UART1 peripheral control registers are mapped to 0xfe650000 address.  

md 0xfe650000 0x20

### 'mm' / 'nm' (Memory modification commands)
Useful for interactively modifying registers.  
mm autoicrements after user presses enter.  
type '-' for previous address.  
leave empty to skip current address.  
press q to drop out of interactive environment.  

### 'cp' (copy memory)
`cp [source] [destination] [size]`


### 'cmp' (compare memory)
`cmp [add1] [add2] [size]`


### Resources
https://docs.u-boot.org/en/latest/usage/index.html#shell-commands


## Environment
U-boot environment is a key-value storage.  
An environment variable can store a script as well.  
A default environment is built into the u-boot binary at compile time.  
An optional environment can be loaded at boot time from storage.
The default and loaded environments are merged and stored in ram, which becomes the final environment for the session.  
The environment can be modified and persisted to storage.  


### Printing environment variables
To print out a variable, you use the:  
`env print [key]`  
command.  

If you ommit the key, it prints out all the environment variables.  

NB: You can also use the `echo $key` syntax to print out environment variables.  


### Setting environment variables
To set a variable, use the command:  
`env set [key] [value]`  


### Other environment mutation commands
`env ask [key] "[prompt]"`  
This command dispalys the prompt comment on the commandline and waits for the user to enter a value.  
When the user presses enter, the value is assigned the variable.  


`env edit [key]`  
This version displays the prompt "edit" and also asigns the value entered to the variable.  


### Removing variables
To remove a variable, simply set it to nothing.  
Removing an example variable "foo" can be done as shown below:  
`env set foo`  

### Persisting environment variables
It is possible to manupulate environment variables and persist them into storage.  
The command to archive this is:  
`saveenv`

NB: saveenv command not available by default. Possibly needs a configuration option when compiling uboot to be active.

TO-DO: Where does uboot know where to persist the environment.

### Running scripts.
To run a script in uboot, the script must first be assigned to an environment variable.  
Then the script can be executed by using the following command:  
`run [key]`  
Commands can in a script can be chained together by using the `;` delimeter.  

### Special environment variables
Certian variables have special meaning in the uboot environment and affect how other commands function.  
Speical variables that are related to booting include:  
  
`bootcmd`:  
This is essentially a script that defines how uboot loads an operating system.  
  
`bootargs`:  
This is the boot arguments that uboot will pass to the linux kernal commandline.  

`preboot`:  
This script is always run onece the hush enviroment has been intialized. any fix ups can be placed here.  

Other special environment varibales include:  
  
`loadaddr`:  
Commands that need to load any data use this variable as the destination memory address for the load operation.  
  
`filesize`:  
Any load operation that completes successfully will set this variable to the size of data that was moved.  

`ver`:  
This variable is always set to the version of uboot.  

There are other special variables that can be investigated later.  

### setexpr
To-DO


# Booting Linux
TO-DO  


# FILE SYSTEM ACCESS

# Device trees
The device trees in the uboot source can be found at  
`dts/upstream/src/arm64`

## Storage controllers / devices
The TF-card (Micro Sd) slot is connected to sdmmc0 controller on the rk3566.  



# Troubleshoot

### Card did not respond to voltage select! : -110

This is issue is probably related to a clock speed issue with the interface for the sd card or emmc device.  
Solved by running the command below to change the speed.  

`mmc dev 1 0 6`  

Link to resource on Stack overflow.  
https://unix.stackexchange.com/questions/762032/uboot-boots-from-sd-card-but-cant-read-info-from-mmc  
