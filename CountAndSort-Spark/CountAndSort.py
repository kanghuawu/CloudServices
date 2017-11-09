import os, shutil, argparse
from operator import add
from pyspark import SparkContext

parser = argparse.ArgumentParser(description='Count URL and Sort by occurrence', prog='CountAndSort.py')
parser.add_argument('--input', help='Input', required=True)
parser.add_argument('--output', help='Output', required=True)
parser.add_argument('--debug', help='Output', action='store_false')
args = parser.parse_args()


def run_spark(sc, input, output):
    lines = sc.textFile(input)
    counts = lines.map(lambda x: x.split(' ')[6]) \
        .map(lambda x: (x, 1)) \
        .reduceByKey(add) \
        .map(lambda x: (x[1], x[0])) \
        .sortByKey() \
        .map(lambda x: (x[1], x[0])) \
        .coalesce(1) \
        .saveAsTextFile(output)
    # output = counts.take(3)
    # for (word, count) in output:
    #     print("%s: %i" % (word, count))


if __name__ == "__main__":
    if args.debug and os.path.exists(args.output):
        shutil.rmtree(args.output)
        print('Output folder removed!')
    sc = SparkContext(appName="CountAndSort")
    run_spark(sc, args.input, args.output)
    sc.stop()
