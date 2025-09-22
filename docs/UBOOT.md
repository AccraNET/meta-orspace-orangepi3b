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

mw [address] [data]  
md [address] [size]  

On the rk3566, UART1 peripheral control registers are mapped to 0xfe650000 address.  

md 0xfe650000 0x20

### 'mm' / 'nm' (Memory modification commands)
Useful for interactively modifying registers.  
mm autoicrements after user presses enter.  
type '-' for previous address.  
leave empty to skip current address.  
press q to drop out of interactive environment.  

### 'cp' (copy memory)
cp [source] [destination] [size]


### 'cmp' (compare memory)
cmp [add1] [add2] [size]


### Resources
https://docs.u-boot.org/en/latest/usage/index.html#shell-commands


## Environment
U-boot environment is a key-value storage.  
An environment variable can store a script as well.  
A default environment is built into the u-boot binary at compile time.  
An optional environment can be loaded at boot time from storage.
The default and loaded environments are merged and stored in ram, which becomes the final environment for the session.  
The environment can be modified and persisted to storage.  



# Troubleshoot

### Card did not respond to voltage select! : -110

This is issue is probably related to a clock speed issue with the interface for the sd card or emmc device.  
Solved by running the command below to change the speed.  

`mmc dev 1 0 6`  

Link to resource on Stack overflow.  
https://unix.stackexchange.com/questions/762032/uboot-boots-from-sd-card-but-cant-read-info-from-mmc  
