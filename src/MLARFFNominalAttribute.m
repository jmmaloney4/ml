//
//  MLARFFNominalAttribute.m
//  ml
//
//  Created by Jack Maloney on 5/17/14.
//
//

#import "MLARFFNominalAttribute.h"

@interface MLARFFNominalAttribute ()

@property (nonatomic, readwrite, strong) NSArray* types;

@end

@implementation MLARFFNominalAttribute

- (instancetype)initWithTypes:(NSArray *)types andName:(NSString *)name
{
    self = [super initWithName:name];
    if (self) {
        self.types = types;
        for (int a = 0; a < self.types.count; a++) {
            if (![self.types[a] isKindOfClass:[NSString class]]) {
                return nil;
            }
        }
    }
    return self;
}

-(void) addData:(id)data {
    if ([data isKindOfClass:[NSString class]]) {
        [self.data addObject:data];
    }
}

@end
