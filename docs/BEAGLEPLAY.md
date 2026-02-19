# BEAGLEPLAY Architecture
## Specifications
- Cortex-A53 ( Quad core)
- Cortex-M4F
- PRU (Programmable Realtime Unit) => Cortex-R5F

## Memory Layout
- At the SOC level, memory map is constructed using 36b physical addresses

## NOTES
- Peripherals are placed at a 64kB aligned addresses to optimize for a mmu configuration of 64kb page sizes
