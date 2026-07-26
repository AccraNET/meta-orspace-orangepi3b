# GPU Device Tree Documentation

This document describes the GPU device tree blocks for the RK3566 SoC used on the Orange Pi 3B.

## Overview

The RK3566 features an ARM Mali-G52 GPU (Bifrost architecture). The device tree configuration is split across multiple files:

| File | Purpose |
|------|---------|
| `rk356x-base.dtsi` | Base GPU node definition |
| `rk3566.dtsi` | SoC-specific OPP table reference |
| `rk3566-orangepi-3b.dtsi` | Board-specific power supply and enablement |

---

## 1. Base GPU Node

**Location:** `other/rk356x-base.dtsi` (line 553-565)

```dts
gpu: gpu@fde60000 {
    compatible = "rockchip,rk3568-mali", "arm,mali-bifrost";
    reg = <0x0 0xfde60000 0x0 0x4000>;
    interrupts = <GIC_SPI 40 IRQ_TYPE_LEVEL_HIGH>,
                 <GIC_SPI 41 IRQ_TYPE_LEVEL_HIGH>,
                 <GIC_SPI 39 IRQ_TYPE_LEVEL_HIGH>;
    interrupt-names = "job", "mmu", "gpu";
    clocks = <&scmi_clk 1>, <&cru CLK_GPU>;
    clock-names = "gpu", "bus";
    #cooling-cells = <2>;
    power-domains = <&power RK3568_PD_GPU>;
    status = "disabled";
};
```

### Property Descriptions

| Property | Value | Description |
|----------|-------|-------------|
| `compatible` | `"rockchip,rk3568-mali", "arm,mali-bifrost"` | Identifies the GPU driver to use. First string is SoC-specific, second is the generic Mali Bifrost driver fallback. |
| `reg` | `<0x0 0xfde60000 0x0 0x4000>` | Memory-mapped I/O region. Base address: `0xfde60000`, Size: `0x4000` (16KB). |
| `interrupts` | Three GIC_SPI interrupts | Hardware interrupt lines for GPU events (see table below). |
| `interrupt-names` | `"job", "mmu", "gpu"` | Labels for each interrupt, used by the driver. |
| `clocks` | `<&scmi_clk 1>, <&cru CLK_GPU>` | Two clock sources: GPU core clock via SCMI interface, bus clock via Clock and Reset Unit (CRU). |
| `clock-names` | `"gpu", "bus"` | Labels for the clocks: "gpu" for core frequency, "bus" for memory interface. |
| `#cooling-cells` | `<2>` | Enables thermal throttling support. The two cells represent minimum and maximum cooling states. |
| `power-domains` | `<&power RK3568_PD_GPU>` | Links to GPU power domain for power management (suspend/resume). |
| `status` | `"disabled"` | GPU is disabled by default; must be enabled in board-specific device tree. |

### Interrupt Mapping

| Interrupt Name | SPI Number | Purpose |
|----------------|------------|---------|
| `job` | 40 | Job completion notification |
| `mmu` | 41 | Memory Management Unit faults |
| `gpu` | 39 | General GPU interrupts |

---

## 2. Operating Points Table Reference

**Location:** `other/rk3566.dtsi` (line 105-107)

```dts
&gpu {
    operating-points-v2 = <&gpu_opp_table>;
};
```

### Description

This links the GPU node to an Operating Performance Points (OPP) table, which defines:

- **Frequency/Voltage pairs** for Dynamic Voltage and Frequency Scaling (DVFS)
- **Performance states** the GPU can operate at
- **Power consumption** characteristics at each operating point

The `gpu_opp_table` is defined elsewhere and typically contains entries like:

```dts
gpu_opp_table: opp-table {
    compatible = "operating-points-v2";
    
    opp-200000000 {
        opp-hz = /bits/ 64 <200000000>;
        opp-microvolt = <825000>;
    };
    opp-400000000 {
        opp-hz = /bits/ 64 <400000000>;
        opp-microvolt = <900000>;
    };
    /* ... more entries ... */
};
```

---

## 3. Board-Specific Configuration

**Location:** `other/rk3566-orangepi-3b.dtsi` (line 170-173)

```dts
&gpu {
    mali-supply = <&vdd_gpu>;
    status = "okay";
};
```

### Property Descriptions

| Property | Value | Description |
|----------|-------|-------------|
| `mali-supply` | `<&vdd_gpu>` | Phandle to the voltage regulator supplying power to the GPU. This regulator is controlled by the DVFS system. |
| `status` | `"okay"` | Enables the GPU for this specific board. |

### Power Supply

The `vdd_gpu` regulator is typically a PMIC-controlled rail that provides:

- Adjustable voltage for DVFS operation
- Typical range: 0.8V - 1.0V depending on frequency
- Controlled by the `rk808` or similar PMIC

---

## Device Tree Inheritance

The GPU configuration follows a hierarchical structure:

```
rk356x-base.dtsi          (base definition, status="disabled")
       ↓
rk3566.dtsi               (adds OPP table reference)
       ↓
rk3566-orangepi-3b.dtsi   (adds power supply, enables GPU)
       ↓
rk3566-orangepi-3b-v1.1.dts  (final board DTS)
```

---

## Related Components

### Video Processing Units

The RK3566 also includes other multimedia hardware near the GPU:

| Component | Address | Description |
|-----------|---------|-------------|
| VPU | `0xfdea0000` | Video Processing Unit (decode) |
| RGA | `0xfdeb0000` | 2D graphics accelerator |
| VEPU | `0xfdee0000` | Video Encoder Processing Unit |

---

## References

- [ARM Mali Bifrost GPU Documentation](https://developer.arm.com/ip-products/graphics-and-multimedia/mali-gpus)
- [Rockchip RK3566 TRM](https://opensource.rock-chips.com/)
- Linux kernel driver: `drivers/gpu/drm/panfrost/`
