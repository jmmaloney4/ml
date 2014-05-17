CC = clang
CFLAGS = -Wall -fobjc-arc

all: main.o ml.o MLARFFFileReader.o MLARFFAttribute.o MLARFFStringAttribute.o MLARFFNumericAttribute.o MLARFFNominalAttribute.o
	$(CC) $(CFLAGS) -o ml main.o ml.o MLARFFFileReader.o MLARFFAttribute.o MLARFFStringAttribute.o MLARFFNumericAttribute.o MLARFFNominalAttribute.o -framework Foundation

main.o: ../src/main.m ../src/ml.h
	$(CC) $(CFLAGS) -c ../src/main.m

ml.o: ../src/ml.h ../src/ml.m
	$(CC) $(CFLAGS) -c ../src/ml.m
    
MLARFFFileReader.o: ../src/MLARFFFileReader.h ../src/MLARFFFileReader.m
	$(CC) $(CFLAGS) -c ../src/MLARFFFileReader.m
	
MLARFFAttribute.o: ../src/MLARFFAttribute.h ../src/MLARFFAttribute.m
	$(CC) $(CFLAGS) -c ../src/MLARFFAttribute.m
	
MLARFFStringAttribute.o: ../src/MLARFFStringAttribute.h ../src/MLARFFStringAttribute.m
	$(CC) $(CFLAGS) -c ../src/MLARFFStringAttribute.m

MLARFFNumericAttribute.o: ../src/MLARFFNumericAttribute.h ../src/MLARFFNumericAttribute.m
	$(CC) $(CFLAGS) -c ../src/MLARFFNumericAttribute.m

MLARFFNominalAttribute.o: ../src/MLARFFNominalAttribute.h ../src/MLARFFNominalAttribute.m
	$(CC) $(CFLAGS) -c ../src/MLARFFNominalAttribute.m
	
# CLEAN
clean:
	rm MLARFFFileReader.o
	rm MLARFFAttribute.o
	rm ml.o
	rm main.o
