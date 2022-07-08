java -jar ./target/game-of-life-benchmark.jar -jvmArgs "--enable-preview -Xms4g -Xmx4g -XX:MaxInlineLevel=15 -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints" -foe true -rf csv -rff results.csv -f 1 -prof "async:output=flamegraph,text;event=cpu;simple=true;dir=.;width=2400;libPath=/home/mario/software/async-profiler-2.8.1-linux-x64/build/libasyncProfiler.so;jstackdepth=50;interval=1000000" gameoflife.benchmark.GameOfLifeBenchmark
