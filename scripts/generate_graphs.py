"""
Generate performance analysis graphs for divide-and-conquer algorithms.
"""

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os
from scipy import stats


os.makedirs('graphs', exist_ok=True)

# Sample data (replace with actual CSV data)
data = {
    'n': [100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000],
    'mergesort_time': [0.08, 0.45, 0.8, 4.5, 9.2, 48, 98, 510, 1052],
    'quicksort_time': [0.06, 0.35, 0.6, 3.8, 7.8, 42, 89, 475, 981],
    'select_time': [0.12, 0.58, 1.2, 5.8, 11, 54, 108, 545, 1090],
    'closest_time': [0.15, 0.92, 1.5, 9.2, 18, 95, 210, 1150, 2450],
    'mergesort_depth': [7, 9, 10, 13, 14, 16, 17, 19, 20],
    'quicksort_depth': [10, 13, 15, 18, 19, 23, 24, 27, 28],
    'select_depth': [5, 7, 8, 9, 10, 11, 12, 13, 14],
    'closest_depth': [7, 9, 10, 13, 14, 16, 17, 19, 20]
}

df = pd.DataFrame(data)

# Set style
plt.style.use('seaborn-v0_8-darkgrid')
colors = ['#e74c3c', '#3498db', '#2ecc71', '#f39c12']

# 1. Time Complexity Comparison (Log-Log Plot)
fig, ax = plt.subplots(figsize=(12, 8))

ax.loglog(df['n'], df['mergesort_time'], 'o-', label='MergeSort', color=colors[0], linewidth=2)
ax.loglog(df['n'], df['quicksort_time'], 's-', label='QuickSort', color=colors[1], linewidth=2)
ax.loglog(df['n'], df['select_time'], '^-', label='Select (MoM)', color=colors[2], linewidth=2)
ax.loglog(df['n'], df['closest_time'], 'd-', label='Closest Pair', color=colors[3], linewidth=2)

# Add theoretical lines
n = df['n'].values
ax.loglog(n, n * np.log2(n) / 10000, '--', alpha=0.5, label='Θ(n log n)', color='gray')
ax.loglog(n, n / 10000, ':', alpha=0.5, label='Θ(n)', color='black')

ax.set_xlabel('Input Size (n)', fontsize=12)
ax.set_ylabel('Time (milliseconds)', fontsize=12)
ax.set_title('Time Complexity: Empirical Performance', fontsize=14, fontweight='bold')
ax.legend(loc='upper left', fontsize=10)
ax.grid(True, alpha=0.3)

plt.tight_layout()
plt.savefig('graphs/time_complexity.png', dpi=150)
plt.close()

# 2. Recursion Depth Analysis
fig, ax = plt.subplots(figsize=(12, 8))

ax.semilogx(df['n'], df['mergesort_depth'], 'o-', label='MergeSort', color=colors[0], linewidth=2)
ax.semilogx(df['n'], df['quicksort_depth'], 's-', label='QuickSort', color=colors[1], linewidth=2)
ax.semilogx(df['n'], df['select_depth'], '^-', label='Select', color=colors[2], linewidth=2)
ax.semilogx(df['n'], df['closest_depth'], 'd-', label='Closest Pair', color=colors[3], linewidth=2)

# Add theoretical line
ax.semilogx(n, np.log2(n), '--', alpha=0.5, label='log₂(n)', color='gray')

ax.set_xlabel('Input Size (n)', fontsize=12)
ax.set_ylabel('Maximum Recursion Depth', fontsize=12)
ax.set_title('Recursion Depth Growth', fontsize=14, fontweight='bold')
ax.legend(loc='upper left', fontsize=10)
ax.grid(True, alpha=0.3)

plt.tight_layout()
plt.savefig('graphs/recursion_depth.png', dpi=150)
plt.close()

# 3. Constant Factor Analysis
fig, ax = plt.subplots(figsize=(12, 8))

# Calculate constant factors (normalized time)
mergesort_cf = df['mergesort_time'] / (df['n'] * np.log2(df['n']) / 1000000)
quicksort_cf = df['quicksort_time'] / (df['n'] * np.log2(df['n']) / 1000000)
select_cf = df['select_time'] / (df['n'] / 1000000)
closest_cf = df['closest_time'] / (df['n'] * np.log2(df['n']) / 1000000)

ax.semilogx(df['n'], mergesort_cf, 'o-', label='MergeSort', color=colors[0], linewidth=2)
ax.semilogx(df['n'], quicksort_cf, 's-', label='QuickSort', color=colors[1], linewidth=2)
ax.semilogx(df['n'], select_cf, '^-', label='Select', color=colors[2], linewidth=2)
ax.semilogx(df['n'], closest_cf, 'd-', label='Closest Pair', color=colors[3], linewidth=2)

ax.set_xlabel('Input Size (n)', fontsize=12)
ax.set_ylabel('Constant Factor', fontsize=12)
ax.set_title('Constant Factor Analysis (Time / Theoretical Complexity)', fontsize=14, fontweight='bold')
ax.legend(loc='upper right', fontsize=10)
ax.grid(True, alpha=0.3)

plt.tight_layout()
plt.savefig('graphs/constant_factors.png', dpi=150)
plt.close()

# 4. Cache Effects Visualization
fig, ax = plt.subplots(figsize=(12, 8))

# Simulated cache boundaries
cache_boundaries = [4000, 32000, 1000000]
cache_labels = ['L1→L2', 'L2→L3', 'L3→RAM']

ax.loglog(df['n'], df['mergesort_time'], 'o-', label='MergeSort', color=colors[0], linewidth=2)
ax.loglog(df['n'], df['quicksort_time'], 's-', label='QuickSort', color=colors[1], linewidth=2)

# Add cache boundary lines
for boundary, label in zip(cache_boundaries, cache_labels):
    ax.axvline(x=boundary, color='red', linestyle='--', alpha=0.5)
    ax.text(boundary, ax.get_ylim()[1] * 0.8, label, rotation=90,
            verticalalignment='top', fontsize=10, color='red')

ax.set_xlabel('Input Size (n)', fontsize=12)
ax.set_ylabel('Time (milliseconds)', fontsize=12)
ax.set_title('Cache Effects on Performance', fontsize=14, fontweight='bold')
ax.legend(loc='upper left', fontsize=10)
ax.grid(True, alpha=0.3)

plt.tight_layout()
plt.savefig('graphs/cache_effects.png', dpi=150)
plt.close()

print("Graphs generated successfully in 'graphs/' directory!")