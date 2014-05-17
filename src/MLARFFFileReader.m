//
//  MLFileReader.m
//  ml
//
//  Created by Jack Maloney on 5/17/14.
//
//

#import "MLARFFFileReader.h"
#import "MLARFFAttribute.h"
#import "MLARFFNominalAttribute.h"
#import "MLARFFNumericAttribute.h"
#import "MLARFFStringAttribute.h"
#import "ml.h"

@interface MLARFFFileReader ()

@property (nonatomic, readwrite) NSString* path;
@property (nonatomic, readwrite) NSMutableArray* attributes;
@property (nonatomic, readwrite) NSString* relation;

@end

@implementation MLARFFFileReader

- (instancetype)initWithPath:(NSString *)path
{
    self = [super init];
    if (self) {
        self.path = path;
    }
    return self;
}

-(void) parse {
    NSScanner* scan = [[NSScanner alloc] initWithString:[NSString stringWithContentsOfFile:self.path encoding:NSUTF8StringEncoding error:nil]];
    BOOL data = NO;
    while (![scan isAtEnd]) {
        // print(@"Loop");
        NSString* x = [NSString string];
        [scan scanUpToString:@"\n" intoString:&x];
        // check if comment
        if (![x hasPrefix:@"%"]) {
            // If the data section is being read
            if (!data) {
                // check if it is an attribute
                if ([[x uppercaseString] hasPrefix:@"@ATTRIBUTE"]) {
                    NSString* attr = ((NSString*) [x componentsSeparatedByString:@" "][1]);
                    NSString* type = [((NSString*) [x componentsSeparatedByString:@" "][2]) uppercaseString];

                    //print(@"%@\n", type);

                    MLARFFAttribute* at;

                    if ([type rangeOfString:STRING_ATTRIBUTE_DATATYPE_SPECIFIER].location != NSNotFound) {
                        // IS String type
                        at = [[MLARFFStringAttribute alloc] init];
                        [self.attributes addObject:at];
                        print(@"String");
                    } else if ([type rangeOfString:NOMINAL_ATTRIBUTE_DATATYPE_SPECIFIER].location != NSNotFound) {
                        // IS Nominal Type
                        at = [[MLARFFNominalAttribute alloc] init];
                        [self.attributes addObject:at];
                        print(@"Nominal");
                    } else if ([type rangeOfString:NUMERIC_ATTRIBUTE_DATATYPE_SPECIFIER].location != NSNotFound) {
                        // IS Numeric Type
                        at = [[MLARFFNumericAttribute alloc] init];
                        [self.attributes addObject:at];
                        print(@"Numeric");
                    } else {
                        [NSException raise:@"ARFF File Format Error" format:@"Attribute %@ of type %@ was not able to be identified", attr, type];
                    }
                }
            }
        } else {
            print(@"Comment\n");
        }
    }
}

@end
