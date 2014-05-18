//
//  MLARFFNominalAttribute.h
//  ml
//
//  Created by Jack Maloney on 5/17/14.
//
//

#import "MLARFFAttribute.h"

@interface MLARFFNominalAttribute : MLARFFAttribute

@property (nonatomic, readonly, strong) NSArray* types;

-(instancetype) initWithTypes:(NSArray*) types andName:(NSString*) name;

@end
