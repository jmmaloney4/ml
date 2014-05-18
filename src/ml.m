#import "ml.h"

void print(NSString* x, ...) {

    va_list(f);
    va_start(f, x);

    printf("%s", [[[NSString alloc] initWithFormat:x arguments:f] UTF8String]);

    fflush(stdout);

    va_end(f);
    
}
