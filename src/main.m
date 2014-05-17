#import <Foundation/Foundation.h>
#import "ml.h"
#import "MLARFFFileReader.h"

int main(int argc, const char * argv[]) {

    if (argc < 2) {
        print(@"Not Enough Arguments\n");
        return 0;
    }

    MLARFFFileReader* reader = [[MLARFFFileReader alloc] initWithPath:[NSString stringWithUTF8String:argv[1]]];

    [reader parse];

    return 0;
}
