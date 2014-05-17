all:
		make --directory=./build/ all

re:
		make clean
		make all

clean:
		make --directory=./build/ clean
