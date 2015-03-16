using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;

namespace DeveloperTestRunner
{
    public static class TestRunner
    {
        internal static void Run(ITests runner)
        {
            var type = runner.GetType();
            var methods = type.GetMethods();
            foreach (var method in methods)
            {
                if (method.DeclaringType == type && !method.IsConstructor && !method.IsSpecialName)
                {
                    try
                    {
                        method.Invoke(runner, new object[] { });
                    }
                    catch (TargetInvocationException e)
                    {
                        runner.Tester.ImplementationThrewException(e.InnerException);
                    }
                }
            }
        }
    }
}
