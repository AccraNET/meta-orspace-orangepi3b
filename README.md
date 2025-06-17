# Boot Sequence of RK3566
BootROM is the first boot stage.  
The bootROM will attempt to load from sector 64 on a detected sd card.  
Sector 64 -> LBA 64 -> 32KB offset  
Therefore any bootloader code (TPL) should be placed at this sector of the sd card image.  
  
  NB: LBA = Logical block address.  
  Starts from 0 .... n  
    
Default block size is 512 bytes.  

# UBOOT Configuration
## TPL
